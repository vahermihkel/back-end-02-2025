import { useEffect, useState } from "react"
import { Product } from "../../models/Product";
import { Link } from "react-router-dom";

function HaldaTooteid() {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/products") // URL
      .then(res => res.json())              // kogu tagastus
      .then(json => setProducts(json))      // body 
  }, []);

  //function deleteProduct() {}

  const deleteProduct = (productId: number) => {
    fetch("http://localhost:8080/products/" + productId, {
      method: "DELETE",
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => setProducts(json)
    )
  }

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Active</th>
            <th>Image</th>
            <th>Category</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {products.map(product => 
            <tr key={product.id}>
              <td>{product.id}</td>
              <td>{product.name}</td>
              <td>{product.price}</td>
              <td>{product.active ? <div>Aktiivne</div> : <div>Mitteaktiivne</div>}</td>
              <td>{product.image}</td>
              <td>{product.category?.name}</td>
              <td><button onClick={() => deleteProduct(Number(product.id))}>x</button></td>
              <td><Link to={"/admin/muuda-toode/" + product.id}><button>Muuda</button></Link></td>
            </tr>)}
        </tbody>
      </table>
    </div>
  )
}

export default HaldaTooteid