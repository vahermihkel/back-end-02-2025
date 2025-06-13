import { useContext, useState } from "react"
import { OrderRow } from "../models/OrderRow";
import Order from "../components/cart/Order";
import { CartSumContext } from "../store/CartSumContext";
import { useDispatch } from "react-redux";
import { decrement, increment } from "../store/CartSumRedux";

function Ostukorv() {
  const dispatch = useDispatch();
  const [cart, setCart] = useState<OrderRow[]>(JSON.parse(localStorage.getItem("cart") || "[]"));
  const {increase, decrease, zero} = useContext(CartSumContext);

  console.log("Ostukorvis");

  const empty = () => {
    setCart([]);
    // localStorage.setItem("cart", JSON.stringify([]))
    // localStorage.setItem("cart", "[]")
    localStorage.removeItem("cart");
    zero();
  }

  const decreaseQuantity = (index: number) => {
    cart[index].quantity--;
    decrease(cart[index].product.price);
    if (cart[index].quantity === 0) {
      deleteProduct(index);
    }
    setCart(cart.slice());
    localStorage.setItem("cart", JSON.stringify(cart));
    dispatch(decrement())
  }

  const increaseQuantity = (index: number) => {
    cart[index].quantity++;
    increase(cart[index].product.price);
    setCart(cart.slice());
    localStorage.setItem("cart", JSON.stringify(cart));
    dispatch(increment())
  }

  const deleteProduct = (index: number) => {
    if (cart[index].quantity > 0) {
      decrease(cart[index].product.price * cart[index].quantity);
    }
    cart.splice(index,1); // mitmendat, mitu tk kustutame
    setCart(cart.slice()); // HTML uuenduseks
    localStorage.setItem("cart", JSON.stringify(cart));
  }

  const calculateCartSum = () => {
    let sum = 0;
    cart.forEach(cartProduct => sum += cartProduct.product.price * cartProduct.quantity)
    return sum.toFixed(2);
  }

  return (
    <div>
      <button onClick={empty}>Tühjenda</button>
      {cart.map((orderRow, index) => 
        <div key={orderRow.product.id}>
          <div>{orderRow.product.name}</div>
          <div>{orderRow.product.price.toFixed(2)}€</div>
          <button onClick={() => decreaseQuantity(index)}>-</button>
          <div>{orderRow.quantity}</div>
          <button onClick={() => increaseQuantity(index)}>+</button>
          <div>{(orderRow.product.price * orderRow.quantity).toFixed(2)}€</div>
          <button onClick={() => deleteProduct(index)}>x</button>
        </div>)}

      <div>Kokku: {calculateCartSum()}€</div>
      <Order cart={cart} />
    </div>
  )
}

// parameetri saatmiseks: onClick={() => decreaseQuantity(index)}
// kui parameetrit ei saada: onClick={decreaseQuantity}
// kui tahangi käima panna: <div>{calculate()}</div>

export default Ostukorv