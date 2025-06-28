import './App.css';
import Header from './components/Header.jsx';
import Footer from './components/Footer.jsx';
import LandingPage from './pages/LandingPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import GamePage from './pages/GamePage.jsx';
import {useState} from "react";

function App() {
    const [page, setPage] = useState("landing");

    return (
        <div>
            <Header onLoginClick={() => setPage("login")} onTitleClick={() => setPage("landing")} />
            {page !== "game" &&
            <div className="scrollable-content">
                {page === "landing" && <LandingPage />}
                {page === "login" && <LoginPage />}
                <Footer />
            </div>
            }
            {page === "game" && <GamePage />}
        </div>
    );
}

export default App;
