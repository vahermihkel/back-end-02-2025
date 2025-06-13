import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Person } from "../../models/Person";

function Signup() {
  const [message, setMessage] = useState("");
  const [person, setPerson] = useState(new Person("", "", "", ""));
  const navigate = useNavigate();

  const signup = () => {

    fetch("http://localhost:8080/signup", {
      method: "POST",
      body: JSON.stringify(person),
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then(json => {
        if (json.message === undefined && json.timestamp === undefined && json.code === undefined) {
          navigate("/login");
        } else {
          setMessage(json.message);
        }
      });
  }

  return (
    <div>
      <div>PERSON: {JSON.stringify(person)}</div>
      <div>{message}</div>
      <label>Firstname</label> <br />
      <input onChange={(e) => setPerson({...person, firstName: e.target.value})} type="text" /> <br />
      <label>Lastname</label> <br />
      <input onChange={(e) => setPerson({...person, lastName: e.target.value})} type="text" /> <br />
      <label>Email</label> <br />
      <input onChange={(e) => setPerson({...person, email: e.target.value})} type="text" /> <br />
      <label>Password</label> <br />
      <input onChange={(e) => setPerson({...person, password: e.target.value})} type="password" /> <br />
      <button onClick={signup}>Signup</button>
    </div>
  )
}

export default Signup