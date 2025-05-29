import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
//import './index.css'
import LoginPage from "./features/LoginPage.jsx";

createRoot(document.getElementById('root')).render(
  <StrictMode>
      <LoginPage />
  </StrictMode>,
)
