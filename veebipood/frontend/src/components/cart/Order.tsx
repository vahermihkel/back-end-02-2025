import { OrderRow } from "../../models/OrderRow";

function Order(props: {cart: OrderRow[]}) {
  const order = () => {
    fetch("http://localhost:8080/orders", {
      method: "POST",
      body: JSON.stringify(props.cart),
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token"),
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then(json => {
        if (json.message === undefined && json.timestamp === undefined && json.code === undefined) {
          console.log("EDUKAS");
          window.location.href = json.link;
        } else {
          console.log("VIGA");
        }
        console.log(json);
      });
  }

  // useNavigate --> Reacti siseseks suunamiseks. JS poole peal
  // <Link to=""> --> Reacti siseseks suunamiseks HTML poole peal.
  
  // window.location.href --> Reacti väliseks suunamiseks. JS poole peal
  // <a href=""> --> Reacti väliseks suunamiseks HTML poole peal.


  return (
    <button onClick={order}>Telli</button>
  )
}

export default Order