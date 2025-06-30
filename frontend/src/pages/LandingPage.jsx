export default function LandingPage() {
    return (
        <main className="main">
            <div className="main-content" >
                <label className="intro-text">
                    Welcome Warrior,<br/>
                    to the amazing world of Die Baumstämme,<br/>
                    where you either dominate your enemies,<br/>
                    or fall victim to their wrath!<br/><br/>
                    Sign up today!
                </label>
            </div>
            <div className="main-content">
                <div className="update-table">
                    <h1>
                        - Recent Updates -
                    </h1>
                    <div className="update-element">
                        <label className="update-element-title">
                            Game Release
                        </label>
                        <label className="update-element-subtitle">
                            Postponed
                        </label>
                    </div>
                    <div className="update-element">
                        <label className="update-element-title">
                            Start of Game Development
                        </label>
                        <label className="update-element-subtitle">
                            May 2025
                        </label>
                    </div>
                </div>
            </div>
        </main>
    );
}