import '../App.css';
import WoodBox from './WoodBox';

export default function Header({onLoginClick, onLogoutClick, onTitleClick, page}) {

    return (
        <header className="header">
            <div className="header-content">
                <button className="title-button" onClick={onTitleClick}>
                    <WoodBox n={10} text="Die Baumstämme" className="header-title" />
                </button>
                {page === "game" ?
                    (<button className="login-button" onClick={onLogoutClick}>
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