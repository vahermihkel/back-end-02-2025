import { useContext, useEffect, useState } from "react";
import { Product } from "../models/Product";
import { OrderRow } from "../models/OrderRow";
import { CartSumContext } from "../store/CartSumContext";
import { Link } from "react-router-dom";

function Avaleht() {
  const [products, setProducts] = useState<Product[]>([]);
  const {increase} = useContext(CartSumContext);

  useEffect(() => {
    fetch("http://localhost:8080/products")
      .then(res => res.json())
      .then(json =>  setProducts(json)
    )
  }, []);

  const addToCart = (product: Product) => {
    const cartLS: OrderRow[] = JSON.parse(localStorage.getItem("cart") || "[]");
    const orderRow = cartLS.find(orderR => orderR.product.id === product.id);
    if (orderRow !== undefined) {
      // suurendame kogust
      orderRow.quantity++;
    } else {
      // lisame ühe juurde, kogusega 1
      cartLS.push(new OrderRow(product, 1));
    }
    increase(product.price);
    localStorage.setItem("cart", JSON.stringify(cartLS));
  }

  return (
    <div>
       {products.map(product => 
        <div key={product.id}>
          <div>{product.name}</div>
          <div>{product.price}€</div>
          <Link to={"/product/" + product.id}>
            <button>Detailsemalt</button>
          </Link>
          <button onClick={() => addToCart(product)}>Lisa ostukorvi</button>
        </div>)} 
    </div>
  )
}

export default Avaleht