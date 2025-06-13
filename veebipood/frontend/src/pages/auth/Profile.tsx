import { useContext, useEffect, useState } from "react";
import { Person } from "../../models/Person";
import { AuthContext } from "../../store/AuthContext";

function Profile() {
  const [message, setMessage] = useState("");
  const [person, setPerson] = useState(new Person("", "", "", ""));
  const {updatePerson} = useContext(AuthContext);

  useEffect(() => {
    fetch("http://localhost:8080/person", {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())           
      .then(json => setPerson(json))    
  }, []);

  const update = () => {

    fetch("http://localhost:8080/person", {
      method: "PUT",
      body: JSON.stringify(person),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => {
        if (json.message === undefined && json.timestamp === undefined && json.code === undefined) {
          setMessage("Edukalt muudetud");
          updatePerson();
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
      <input defaultValue={person.firstName} onChange={(e) => setPerson({...person, firstName: e.target.value})} type="text" /> <br />
      <label>Lastname</label> <br />
      <input defaultValue={person.lastName} onChange={(e) => setPerson({...person, lastName: e.target.value})} type="text" /> <br />
      <label>Email</label> <br />
      <input defaultValue={person.email} onChange={(e) => setPerson({...person, email: e.target.value})} type="text" /> <br />
      {/* <label>Password</label> <br />
      <input onChange={(e) => setPerson({...person, password: e.target.value})} type="password" /> <br /> */}
      <button onClick={update}>Update</button>
    </div>
  )
}

export default Profile