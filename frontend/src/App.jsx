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
    const [currentGameId, setCurrentGameId] = useState(null);

    const handleGameSelection = (gameId) => {
        setCurrentGameId(gameId); // Set the gameId received from GameSelectionPage
        setPage("game"); // Transition to the game page
    };

    return (
        <div>
            <Header onLoginClick={() => setPage("login")} onLogout={() => setPage("login")} onTitleClick={() => setPage("landing")} page={page} />
            {page !== "game" &&
            <div className="scrollable-content">
                {page === "landing" && <LandingPage />}
                {page === "login" && <LoginPage onLogin={() => setPage("selection")} />}
                {page === "selection" && <GameSelectionPage onGameSelection={handleGameSelection} />}
                <Footer />
            </div>
            }
            {page === "game" && <GamePage gameId={currentGameId} />}
        </div>
    );
}

export default App;
