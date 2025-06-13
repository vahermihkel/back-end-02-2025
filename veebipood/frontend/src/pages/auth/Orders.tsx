import { useEffect, useState } from "react";
import { Order } from "../../models/Order";

function Orders() {
  // const [message, setMessage] = useState("");
  const [orders, setOrders] = useState<Order[]>([]);
  
  useEffect(() => {
    fetch("http://localhost:8080/orders", {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())           
      .then(json => setOrders(json))    
  }, []);
  
  const getFormattedDate = (javaDate: Date) => {
    const date = new Date(javaDate);
    return date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear();
  }

  return (
    <div>
      {orders.map(order => 
        <div key={order.id}>
          <div>ID: {order.id}</div>
          <div>{getFormattedDate(order.created)}</div>
          <div>Kokku: {order.totalSum}€</div>
          <div>{order.orderRows.map(orderRow => 
              <div key={orderRow.product.id}>
                <div>{orderRow.product.name}</div>
                <div>{orderRow.product.price.toFixed(2)}€</div>
                <div>{orderRow.quantity}</div>
                <div>{(orderRow.product.price * orderRow.quantity).toFixed(2)}€</div>
              </div>)}</div>
        </div>
      )}
    </div>
  )
}

export default Orders