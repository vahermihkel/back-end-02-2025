//import React from 'react' // imporditakse terve React
import { useEffect, useState } from "react"; // imporditakse moodule Reactist
import { Product } from "../models/Product";
import { useParams } from "react-router-dom";

function SingleProduct() {
  const [product, setProduct] = useState<Product>(new Product("", 0, "", false, {id: 0,name: "",active:false}));
  const {id} = useParams(); // <Route path='/product/:id'. object destructuring
  // const params = useParams();  // params.id

  useEffect(() => {
    fetch("http://localhost:8080/products/" + id)
      .then(res => res.json())
      .then(json =>  {
        setProduct(json);
        console.log("VÕTAB AEGA - mingite ms pärast järgi");
      }
    )
    console.log("useEffect");
  }, [id]);

  if (product.id === undefined) {
    return <div>Loading...</div>
  }

  return (
    <div>
      <div>{product.id}</div>
      <div>{product.name}</div>
      <div>{product.category?.name}</div>
      <div>{product.price}€</div>
      <div>{product.image}</div>
    </div>
  )
}

export default SingleProduct