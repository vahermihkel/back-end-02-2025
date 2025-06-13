import { useEffect, useState } from "react"
import { Product } from "../../models/Product";
import { Category } from "../../models/Category";

function LisaToode() {
  const [message, setMessage] = useState("");
  const [product, setProduct] = useState<Product>({
    name: "",
    price: 0,
    image: "",
    active: false,
    category: { id: 0, name: "", active: false },
  });
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
      fetch("http://localhost:8080/categories")
        .then(res => res.json())           
        .then(json => setCategories(json))    
    }, []);

  const add = () => {
    fetch("http://localhost:8080/products", {
      method: "POST",
      body: JSON.stringify(product),
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
          setMessage("");
        }
      })
  }

  return (
    <div>
      <div>TOODE: {JSON.stringify(product)}</div>
      <div>{message}</div>
      <label>Name</label> <br />
      <input onChange={(e) => setProduct({...product, name: e.target.value})} type="text" /> <br />
      <label>Price</label> <br />
      <input onChange={(e) => setProduct({...product, price: Number(e.target.value)})} type="number" /> <br />
      <label>Image</label> <br />
      <input onChange={(e) => setProduct({...product, image: e.target.value})} type="text" /> <br />
      <label>Active</label> <br />
      <input onChange={(e) => setProduct({...product, active: e.target.checked})} type="checkbox" /> <br />
      <label>Category</label> <br />
      {/* <input type="text" /> <br /> */}
      <select onChange={(e) => setProduct({...product, category: {...product.category, id: Number(e.target.value)}})}>
        {categories.map(category => <option key={category.id} value={category.id}>{category.name}</option>)}
      </select>
      <button onClick={add}>Add</button> <br />
    </div>
  )
}

export default LisaToode