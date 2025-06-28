import '../App.css';
import WoodBox from './WoodBox';

export default function Header({onLoginClick, onTitleClick, page}) {

    return (
        <header className="header">
            <div className="header-content">
                <button className="title-button" onClick={onTitleClick}>
                    <WoodBox n={10} text="Die Baumstämme" className="header-title" />
                </button>
                {page === "game" ?
                    (<button className="login-button" onClick={onTitleClick}>
                        <WoodBox n={4} text="Profil" />
                    </button>)
                    :
                    (<button className="login-button" onClick={onLoginClick}>
                        <WoodBox n={4} text="Login" />
                    </button>)}
            </div>
        </header>
    )
}