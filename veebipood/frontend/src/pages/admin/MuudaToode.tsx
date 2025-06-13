import { useEffect, useState } from "react";
import { Product } from "../../models/Product";
import { Category } from "../../models/Category";
import { useNavigate, useParams } from "react-router-dom";

// rfce
function MuudaToode() {
  const [message, setMessage] = useState("");
  const [product, setProduct] = useState<Product>({
      name: "",
      price: 0,
      image: "",
      active: false,
      category: { id: 0, name: "", active: false },
    });

  const [categories, setCategories] = useState<Category[]>([]);
  const {id} = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8080/products/" + id)
      .then(res => res.json())
      .then(json =>  {
        setProduct(json);
      }
    )
  }, [id]);
  
  useEffect(() => {
      fetch("http://localhost:8080/categories")
        .then(res => res.json())           
        .then(json => setCategories(json))    
  }, []);

  const edit = () => {
    fetch("http://localhost:8080/products", {
      method: "PUT",
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
          navigate("/admin/halda-tooteid");
        }
      })
  }

  if (product.id === undefined) {
    return <div>Loading...</div>
  }

  return (
    <div>
       <div>{message}</div>
      <label>Name</label> <br />
      <input defaultValue={product.name} onChange={(e) => setProduct({...product, name: e.target.value})} type="text" /> <br />
      <label>Price</label> <br />
      <input defaultValue={product.price} onChange={(e) => setProduct({...product, price: Number(e.target.value)})} type="number" /> <br />
      <label>Image</label> <br />
      <input defaultValue={product.image} onChange={(e) => setProduct({...product, image: e.target.value})} type="text" /> <br />
      <label>Active</label> <br />
      <input defaultChecked={product.active} onChange={(e) => setProduct({...product, active: e.target.checked})} type="checkbox" /> <br />
      <label>Category</label> <br />
      {/* <input type="text" /> <br /> */}
      <select defaultValue={product.category?.id} onChange={(e) => setProduct({...product, category: {...product.category, id: Number(e.target.value)}})}>
        {categories.map(category => <option key={category.id} value={category.id}>{category.name}</option>)}
      </select> <br />
      <button onClick={edit}>Edit</button> <br />
    </div>
  )
}

export default MuudaToode