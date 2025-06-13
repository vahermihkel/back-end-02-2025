import { useContext } from "react"
import { Link, useNavigate } from "react-router-dom"
import { CartSumContext } from "../store/CartSumContext"
import { AuthContext } from "../store/AuthContext";
import { useTranslation } from 'react-i18next';
import { useSelector } from "react-redux";

function Menu() {
  const count = useSelector(state => state.counter.value)
  const {cartSum} = useContext(CartSumContext);
  const {person, updatePerson} = useContext(AuthContext);
  const navigate = useNavigate();
  const { t, i18n } = useTranslation();

  const logout = () => {
    sessionStorage.removeItem("token");
    updatePerson();
    navigate("/");
  }

  const updateLanguage = (newLang: string) => {
    i18n.changeLanguage(newLang);
    localStorage.setItem("language", newLang)
  }

  return (
    <div>
      <Link to="/"> <button>{t("nav.homepage")}</button> </Link>
      <Link to="/ostukorv"> <button>{t("nav.cart")}</button> </Link>
      { person.role === "ADMIN" || person.role === "SUPERADMIN" && <Link to="/admin"> <button>Admin avalehele</button> </Link>}
      <Link to="/profile"> <button>{t("nav.profile")}</button> </Link>
      <Link to="/orders"> <button>{t("nav.orders")}</button> </Link>
      <Link to="/map"> <button>{t("nav.map")}</button> </Link>
      <span>{t("nav.total")}: {cartSum.toFixed(2)}€</span>
      <span>{count}</span>
      {person.id === undefined ? 
        <>
          <Link to="/login"><button>{t("nav.login")}</button></Link>
          <Link to="/signup"> <button>{t("nav.signup")}</button> </Link>
        </>
        : <span>
          {t("nav.logged-in")}: {person.firstName}
          <button onClick={logout}>{t("nav.logout")}</button>
          </span>
      }
      <button onClick={() => updateLanguage("et")}>EST</button>
      <button onClick={() => updateLanguage("en")}>ENG</button>
    </div>
  )
}

export default Menu