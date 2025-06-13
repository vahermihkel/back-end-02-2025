import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom"


function AdminHome() {
  const { t } = useTranslation();
  
  return (
    <div>
      <Link to="/admin/lisa-toode"> <button>{t("admin.add-product")}</button> </Link>
      <Link to="/admin/halda-tooteid"> <button>{t("admin.manage-products")}</button> </Link>
      <Link to="/admin/halda-kategooriaid"> <button>{t("admin.manage-categories")}</button> </Link>
      <Link to="/admin/halda-admine"> <button>{t("admin.manage-admins")}</button> </Link>
    </div>
  )
}

export default AdminHome