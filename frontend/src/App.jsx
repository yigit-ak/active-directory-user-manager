import './App.css'
import { Routes, Route } from "react-router-dom"

import CreateUser from './pages/CreateUser.jsx'
import Home from './pages/Home.jsx'
import NotFound from './pages/NotFound.jsx'
import UserDetails from './pages/UserDetails.jsx'
import UserLookup from './pages/UserLookup.jsx'

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/create-user" element={<CreateUser />} />
        <Route path="/user-lookup" element={<UserLookup />} />
        <Route path="/user-lookup/:id" element={<UserDetails />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  )
}

export default App
