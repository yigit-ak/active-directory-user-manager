import './App.css'
import { Routes, Route } from "react-router-dom"

import CreateUser from './pages/CreateUser'
import Home from './pages/Home'
import NotFound from './pages/NotFound.jsx'
import UserDetails from './pages/UserDetails'
import UserLookup from './pages/UserLookup'
import Header from './components/layout/Header.jsx'
import { useEffect } from 'react'
import { checkAuth } from './api.js'

function App() {
useEffect(() => {
  checkAuth().catch(() => {
    window.location.href = 'http://localhost:8080/oauth2/authorization/azure';
  });
}, []);

  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/create-user" element={<CreateUser />} />
        <Route path="/user-lookup" element={<UserLookup />} />
        <Route path="/user-lookup/:cn" element={<UserDetails />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  )
}

export default App
