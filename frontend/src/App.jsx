import './App.css';
import Header from './components/Header.jsx';
import Footer from './components/Footer.jsx';
import LandingPage from './pages/LandingPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import GamePage from './pages/GamePage.jsx';
import {useState} from "react";
import GameSelectionPage from "./pages/GameSelectionPage.jsx";

function App() {
    const [page, setPage] = useState("landing");

    return (
        <div>
            <Header onLoginClick={() => setPage("login")} onLogoutClick={() => setPage("login")} onTitleClick={() => setPage("landing")} page={page} />
            {page !== "game" &&
            <div className="scrollable-content">
                {page === "landing" && <LandingPage />}
                {page === "login" && <LoginPage onLogin={() => setPage("selection")} />}
                {page === "selection" && <GameSelectionPage onGameSelection={() => setPage("game")} />}
                <Footer />
            </div>
            }
            {page === "game" && <GamePage />}
        </div>
    );
}

export default App;
