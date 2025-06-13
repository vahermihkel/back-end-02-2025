import './App.css'
import { Route, Routes } from 'react-router-dom';
import Avaleht from './pages/Avaleht';
import Ostukorv from './pages/Ostukorv';
import AdminHome from './pages/admin/AdminHome';
import LisaToode from './pages/admin/LisaToode';
import HaldaTooteid from './pages/admin/HaldaTooteid';
import HaldaKategooriaid from './pages/admin/HaldaKategooriaid';
import Login from './pages/auth/Login';
import Signup from './pages/auth/Signup';
import Profile from './pages/auth/Profile';
import Orders from './pages/auth/Orders';
import Menu from './components/Menu';
import SingleProduct from './pages/SingleProduct';
import HaldaAdmine from './pages/admin/HaldaAdmine';
import { useContext } from 'react';
import { AuthContext } from './store/AuthContext';
import MuudaToode from './pages/admin/MuudaToode';
import Map from './pages/Map';

// Reacti hookide reeglid:
// 1. peab olema imporditud
// 2. peab algama use- eesliidesega
// 3. peab olema funktsionaalne ehk olema sulud lõpus
// 4. ei tohi olla tingimuslikult loodud
// 5. ei tohi neid luua funktsiooni sees

function App() {
  const {person} = useContext(AuthContext);

  return (
    <>
      <Menu />

      <Routes>
        <Route path='/' element={ <Avaleht /> } />
        <Route path='/ostukorv' element={ <Ostukorv /> } />
        
        {person.role === "ADMIN" || person.role === "SUPERADMIN" &&
        <>
          <Route path='/admin' element={ <AdminHome /> } />
          <Route path='/admin/lisa-toode' element={ <LisaToode /> } />
          <Route path='/admin/muuda-toode/:id' element={ <MuudaToode /> } />
          <Route path='/admin/halda-tooteid' element={ <HaldaTooteid /> } />
          <Route path='/admin/halda-kategooriaid' element={ <HaldaKategooriaid /> } />
          <Route path='/admin/halda-admine' element={ <HaldaAdmine /> } />
        </>}

        <Route path='/login' element={ <Login /> } />
        <Route path='/signup' element={ <Signup /> } />
        <Route path='/profile' element={ <Profile /> } />
        <Route path='/orders' element={ <Orders /> } />
        <Route path='/map' element={ <Map /> } />

        <Route path='/product/:id' element={ <SingleProduct /> } />

        <Route path="*" element={ <div>Page not found</div> } />
      </Routes>
    </>
  )
}

export default App
