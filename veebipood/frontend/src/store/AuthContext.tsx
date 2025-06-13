import { createContext, ReactNode, useEffect, useState } from "react";
import { Person } from "../models/Person";

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext({
  person: new Person("","","","", ""),
  updatePerson: () => {}
});

interface ContextArguments {
  children: ReactNode
}

export const AuthContextProvider = ({children}: ContextArguments) => {
  const [person, setPerson] = useState<Person>(new Person("","","","", ""));

  useEffect(() => {
    updatePerson();
  }, []);

  function updatePerson() {
    if (sessionStorage.getItem("token") === null) {
      setPerson(new Person("","","","", ""))
      return;
    }
    fetch("http://localhost:8080/person", {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => setPerson(json))
  }

  return (
    <AuthContext.Provider value={{person, updatePerson}}>
      {children}
    </AuthContext.Provider>
  )
}