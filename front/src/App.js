import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/post/Home";
import SaveForm from "./pages/post/SaveForm";
import Detail from "./pages/post/Detail";
import LoginForm from "./pages/user/LoginForm";
import JoinForm from "./pages/user/JoinForm";
import UpdateForm from "./pages/post/UpdateForm";
import PrivateRoute from "./actions/PrivateRoute";

function App() {
  return (
      <BrowserRouter>
          <Header />
          <div className="App">
            <Routes>
                <Route path="/" element={ <Home /> }/>
                <Route path="/save" element={ <SaveForm />}/>
                <Route path="/post/:id" element={ <Detail /> }/>
                <Route path="/login" element={ <LoginForm/> }/>
                <Route path="/join" element={ <JoinForm /> }/>
                <Route element={ <PrivateRoute /> }>
                    <Route path="/updateForm/:id" element={ <UpdateForm />}/>
                </Route>
            </Routes>
        </div>
      </BrowserRouter>
  );
}

export default App;
