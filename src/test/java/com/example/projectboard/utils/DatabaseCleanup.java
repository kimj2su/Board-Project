package com.example.projectboard.utils;

import com.google.common.base.CaseFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DatabaseCleanup implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;
    private Map<String, String> tableColumnMap;

    @Override
    public void afterPropertiesSet() {
        //entityManager에서 엔티티들을 다 가져옵니다.
        final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        //리스트에 담는다.
        tableNames = entities.stream()
                .filter(e -> isEntity(e) && hasTableAnnotation(e))
                .map(e -> {
                    String tableName = e.getJavaType().getAnnotation(Table.class).name();
                    return tableName.isBlank() ? CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()) : tableName;
                })
                .collect(Collectors.toList());

        //리스트에서 테이블 어노테이션이 없으면 SubwayLine -> subway_line 으로 변경해준다.
        final List<String> entityNames = entities.stream()
                .filter(e -> isEntity(e) && !hasTableAnnotation(e))
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
                .collect(Collectors.toList());

        tableNames.addAll(entityNames);
        getTableColumnName();
    }

    private void getTableColumnName() {
        final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        tableColumnMap = new HashMap<>();

        tableColumnMap = entities.stream()
                .filter(e -> isEntity(e) && hasTableAnnotation(e))
                .collect(Collectors.toMap(
                        e -> {
                            String tableName = e.getJavaType().getAnnotation(Table.class).name();
                            return tableName.isBlank() ? CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()) : tableName;
                        },
                        e -> {
                            Class<?> entityClass = e.getJavaType();
                            return Arrays.stream(entityClass.getDeclaredFields())
                                    .filter(field -> field.isAnnotationPresent(Id.class))
                                    .map(field -> {
                                        Column idColumn = field.getAnnotation(Column.class);
                                        return idColumn != null ? idColumn.name() : field.getName();
                                    })
                                    .findFirst()
                                    .orElseGet(() -> "");
                        }
                ));

        tableColumnMap.putAll(entities.stream()
                .filter(e -> isEntity(e) && !hasTableAnnotation(e))
                .collect(Collectors.toMap(
                        e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()),
                        e -> {
                            Class<?> entityClass = e.getJavaType();
                            return Arrays.stream(entityClass.getDeclaredFields())
                                    .filter(field -> field.isAnnotationPresent(Id.class))
                                    .map(field -> {
                                        Column idColumn = field.getAnnotation(Column.class);
                                        return idColumn != null ? idColumn.name() : field.getName();
                                    })
                                    .findFirst()
                                    .orElseGet(() -> "");
                        }
                )));
    }

    private boolean isEntity(final EntityType<?> e) {
        //지금 현재 자바타입에서 @Entity 어노테이션이 붙어있는지 확인합니다.
        return null != e.getJavaType().getAnnotation(Entity.class);
    }

    private boolean hasTableAnnotation(final EntityType<?> e) {
        //지금 현재 자바타입에서 @Table 어노테이션이 붙어있는지 확인합니다.
        return null != e.getJavaType().getAnnotation(Table.class);
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        //참조 무결성때문에 해당 테이블을 지우려고하는데 뭔가 다른 테이블들하고 fk로 연결되어 있으면 지워지지 않기 때문에 무시하게 해야한다.
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (final String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            // @Id 시퀀스를 1로 업데이트해준다.
            entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN " + tableColumnMap.get(tableName) + " RESTART WITH 1").executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

}
