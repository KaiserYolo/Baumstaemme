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
            <Header onLoginClick={() => setPage("game")} onTitleClick={() => setPage("landing")} />

            <div className="scrollable-content">
                {page === "landing" && <LandingPage />}
                {page === "login" && <LoginPage />}
                {page === "game" && <GamePage />}

                <Footer />
            </div>
        </div>
    );
}

export default App;
