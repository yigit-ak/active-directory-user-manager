import './App.css'
import { Routes, Route } from "react-router-dom"

import { CreateUser } from './pages/CreateUser'
import { Home } from './pages/Home'
import { NotFound } from './pages/NotFound'
import { UserDetails } from './pages/UserDetails'
import { UserLookup } from './pages/UserLookup'

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
