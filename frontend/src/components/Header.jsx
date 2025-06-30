import '../App.css';
import WoodBox from './WoodBox';
import {logoutUser} from "../services/AuthAPI.js";



export default function Header({onLoginClick, onTitleClick, page, onLogout}) {

    const logOut = async () => {
        await logoutUser();
        onLogout();
    }

    return (
        <header className="header">
            <div className="header-content">
                <button className="title-button" onClick={onTitleClick}>
                    <WoodBox n={10} text="Die Baumstämme" className="header-title" />
                </button>
                {(page === "game" || page === "selection" ) ?
                    (<button className="login-button" onClick={logOut}>
                        <WoodBox n={4} text="Logout" />
                    </button>)
                    :
                    (<button className="login-button" onClick={onLoginClick}>
                        <WoodBox n={4} text="Login" />
                    </button>)}
            </div>
        </header>
    )
}