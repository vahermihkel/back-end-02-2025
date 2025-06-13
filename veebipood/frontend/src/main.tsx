import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'leaflet/dist/leaflet.css';
import './index.css'
import './i18n';
import App from './App.tsx'
import { BrowserRouter } from 'react-router-dom'
import { CartSumContextProvider } from './store/CartSumContext.tsx'
import { AuthContextProvider } from './store/AuthContext.tsx'
import store from './store/store'
import { Provider } from 'react-redux'

// ./ või ../  --> meie failidest
// "react"   "react-router-dom" --> node_module'st
// { BrowserRouter } --> tüki moodulist. export käib const abil
// App --> terve faili. export käib default abil
// import ... from "..." --> eksisteerib ainult siin failis
// import "./..." --> eksisteerib üle terve rakenduse

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <CartSumContextProvider>
        <AuthContextProvider>
          <Provider store={store}>
            <App />
          </Provider>
        </AuthContextProvider>
      </CartSumContextProvider>
    </BrowserRouter>
  </StrictMode>,
)


// Boostrap -> ilusa menüü
// MUI ->

// Lisamine

// Ostukorvi lisamine -> localStorage-sse ostukorvi