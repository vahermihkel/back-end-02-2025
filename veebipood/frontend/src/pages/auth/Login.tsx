import { useContext, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../store/AuthContext";

function Login() {
  const [message, setMessage] = useState("");
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const navigate = useNavigate();
  const {updatePerson} = useContext(AuthContext);

  const login = () => {
    if (emailRef.current === null || passwordRef.current === null) {
      return;
    }

    const payload = {
      "email": emailRef.current.value,
      "password": passwordRef.current.value
    }

    fetch("http://localhost:8080/login", {
      method: "POST",
      body: JSON.stringify(payload),
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then(json => {
        if (json.token && json.expiration) {
          //window.location.href = "/admin" <-- teeb refreshi
          navigate("/profile");
          sessionStorage.setItem("token", json.token);
          updatePerson();
        } else {
          setMessage(json.message);
        }
      });
  }

  return (
    <div>
      <div>{message}</div>
      <label>Email</label> <br />
      <input ref={emailRef} type="text" /> <br />
      <label>Password</label> <br />
      <input ref={passwordRef} type="password" /> <br />
      <button onClick={login}>Login</button>
    </div>
  )
}

export default Login