import { useEffect, useRef, useState } from "react"
import { Category } from "../../models/Category";

function HaldaKategooriaid() {
  const [categories, setCategories] = useState<Category[]>([]);
  const nameRef = useRef<HTMLInputElement>(null);
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/categories") // URL
      .then(res => res.json())              // kogu tagastus
      .then(json => setCategories(json))      // body 
  }, []);

  const deleteCategory = (categoryId: number) => {
    fetch("http://localhost:8080/categories/" + categoryId, {
      method: "DELETE",
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => {
        setCategories(json);
        setMessage("");
      })
  }

  const add = () => {
    const nameInput = nameRef.current;
    if (nameInput === null) {
      return;
    }

    const payload = {
      name: nameInput.value,
      active: true
    }

    fetch("http://localhost:8080/categories", {
      method: "POST",
      body: JSON.stringify(payload),
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token"),
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then(body => {
        if (body.message && body.timestamp && body.code) {
          setMessage(body.message);
        } else {
          setCategories(body);
          nameInput.value = "";
          setMessage("");
        }
      })
  }

  return (
    <div>
      <div>{message}</div>
      <label>Category</label> <br />
      <input ref={nameRef} type="text" /> <br />
      <button onClick={add}>Add</button> <br />
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Active</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {categories.map(category => 
            <tr key={category.id}>
              <td>{category.id}</td>
              <td>{category.name}</td>
              <td>{category.active}</td>
              <td><button onClick={() => deleteCategory(category.id)}>x</button></td>
            </tr>)}
        </tbody>
      </table>
    </div>
  )
}

export default HaldaKategooriaid

// onClick={() => deleteCategory(id)} -> argument saadetakse
// onClick={addCategory}              -> argumenti ei saadeta
// onClick={() => addCategory()}
// EI SAA: onClick={deleteCategory(id)}