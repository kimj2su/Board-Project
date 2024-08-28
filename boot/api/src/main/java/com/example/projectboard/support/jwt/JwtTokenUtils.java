package com.example.projectboard.support.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

/**
 * 토큰에 유저네임과 키와 유효시간을 넣어준다.
 */
public class JwtTokenUtils {


  public static String getEmail(String token, String key) {
    return extractClaims(token, key).get("email", String.class);
  }

  public static Object getClaim(String token, String key, String claimName, Class<?> type) {
    return extractClaims(token, key).get(claimName, type);
  }

  public static boolean isExpired(String token, String key) {
    Date expiredDate = extractClaims(token, key).getExpiration();
    return expiredDate.before(new Date());
  }

  private static Claims extractClaims(String token, String key) {
    return Jwts.parser().verifyWith(getKey(key))
        .build().parseSignedClaims(token).getPayload();
  }

  public static String generateToken(String email, String key, long expiredTimeMS) {
//         Claims claims = Jwts.claims();
//         claims.put("email", email);

//         Jwts.builder()
//                 .setClaims(claims)
//                 .setIssuedAt(new Date(System.currentTimeMillis()))
//                 .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMS))
//                 .signWith(getKey(key), SignatureAlgorithm.HS256)
//                 .compact();
    ClaimsBuilder claimsBuilder = Jwts.claims();
    claimsBuilder.add("email", email);
    Claims claims = claimsBuilder.build();

    return Jwts.builder().claims(claims)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredTimeMS))
        .signWith(getKey(key), Jwts.SIG.HS256)
        .compact();
  }

  private static SecretKey getKey(String key) {
    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}