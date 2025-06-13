import { useEffect, useState } from "react";
import { Person } from "../../models/Person";

function HaldaAdmine() {
  const [persons, setPersons] = useState<Person[]>([]);
  
  useEffect(() => {
    fetch("http://localhost:8080/admin-persons", {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())           
      .then(json => setPersons(json))    
  }, []);

  const changeAdmin = (id: number) => {                         // TODO: true asemel midagi muud
    fetch("http://localhost:8080/change-admin?id=" + id + "&isAdmin=" + true, {
      method: "PATCH",
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => setPersons(json)
    )
  }
  

  return (
    <div>
       <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Change Admin</th>
          </tr>
        </thead>
        <tbody>
          {persons.map(person => 
            <tr key={person.id}>
              <td>{person.id}</td>
              <td>{person.firstName}</td>
              <td>{person.lastName}</td>
              <td>{person.email}</td>
              <td>{person.role}</td>
              <td><button onClick={() => changeAdmin(Number(person.id))}>x</button></td>
            </tr>)}
        </tbody>
      </table>
    </div>
  )
}

export default HaldaAdmine