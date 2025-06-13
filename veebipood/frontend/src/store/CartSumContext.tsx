import { createContext, ReactNode, useState } from "react";
import { OrderRow } from "../models/OrderRow";

// eslint-disable-next-line react-refresh/only-export-components
export const CartSumContext = createContext({
  cartSum: 0,
  increase: (price: number) => {console.log(price)},
  decrease: (price: number) => {console.log(price)},
  zero: () => {}
});

// {children: <div></div>}  ---> {children}
// const children = <div></div>

// :{children: ReactNode}

interface ContextArguments {
  children: ReactNode
}

export const CartSumContextProvider = ({children}: ContextArguments) => {
  
  const calculateCartSum = () => {
    const cartLS: OrderRow[] = JSON.parse(localStorage.getItem("cart") || "[]");
    let sum = 0;
    cartLS.forEach(cartProduct => sum += cartProduct.product.price * cartProduct.quantity)
    return sum;
  }

  const [cartSum, setCartSum] = useState(calculateCartSum());

  const increase = (price: number) => {
    setCartSum(cartSum + price);
  }

  const decrease = (price: number) => {
    setCartSum(cartSum - price);
  }
  
  const zero = () => {
    setCartSum(0);
  }

  return (
    <CartSumContext.Provider value={{cartSum, increase, decrease, zero}}>
      {children}
    </CartSumContext.Provider>
  )
}