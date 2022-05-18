import React from "react";
import "../Login/LoginPage.css";

const HomePage = ({ setFakeToken }) => {
	const onLogOut = () => {
		localStorage.removeItem("accessToken");
		setFakeToken("");
	};

	return (
		<React.Fragment>
			<nav className="navbar sticky-top navbar-expand-md navbar-dark text-light  gradient-custom">
				<div className="container">
					<a href="" className="navbar-brand text-warning">
						SKYNET
					</a>
					<button className="navbar-toggler" data-toggle="collapse" data-target="#mynav3">
						<span className="navbar-toggler-icon"></span>
					</button>
					<div className="collapse navbar-collapse" id="mynav3">
						<ul className="navbar-nav">
							<li className="nav-item">
								<a href="#" className="nav-link active">
									Home
								</a>
							</li>
							<li className="nav-item">
								<a href="#" className="nav-link">
									About
								</a>
							</li>
						</ul>
						<form onSubmit={(e) => e.preventDefault()} className="form-inline ml-auto">
							<input type="text" className="form-control" />
							<button className="btn btn-warning ml-2">Search</button>
							<button className="btn btn-danger ml-2" onClick={onLogOut}>
								Log Out
							</button>
						</form>
					</div>
				</div>
			</nav>
			<div className="container">
				<p>
					<span className="display-4 text-center d-block my-3 bg-secondary text-success font-weight-bold">TOKEN FOUND</span>
					Lorem ipsum dolor sit amet consectetur adipisicing elit. Quas voluptas, porro sint, ex eveniet, magni amet nemo unde ipsa temporibus saepe
					numquam. Iusto iure, accusamus reiciendis quo asperiores modi aliquid esse provident laudantium quidem distinctio earum dolor nostrum
					soluta, ipsum aut magnam incidunt praesentium illum quis odio debitis eligendi animi hic? Vero quasi magnam impedit possimus deleniti,
					facilis porro, natus reprehenderit voluptates doloremque, repellendus maxime dolores ullam quis cum. Numquam sit neque illo unde ex vero
					blanditiis atque reprehenderit maiores laboriosam debitis hic optio odio commodi minus, voluptates quam. Tempore iusto necessitatibus
					laudantium earum reiciendis assumenda illo praesentium mollitia, incidunt ipsum sint corrupti fugit animi nihil. In nemo doloribus nostrum
					perferendis inventore officiis optio minus illum enim soluta, suscipit doloremque similique labore nisi corrupti qui! Non nostrum, neque
					consequatur eum iure possimus error pariatur laboriosam reiciendis tempora nobis voluptatibus. Cumque perferendis, impedit consequuntur
					adipisci minima nemo quae obcaecati maiores nostrum possimus neque fugit omnis aperiam. Nulla veniam eos voluptatem nostrum amet cupiditate
					vitae tempora numquam similique. Temporibus dolores in optio ut. Cumque aspernatur nostrum ratione ullam amet itaque ex, vel, in sint
					recusandae inventore eligendi quam explicabo consequatur. Maxime deleniti quasi obcaecati expedita eum quam mollitia est aspernatur
					molestias accusamus id neque illum sapiente aut inventore soluta aliquid odit rem, minima fugit voluptates sint hic! Repudiandae praesentium
					vero rerum facere, eveniet quibusdam nisi ab maiores cupiditate amet neque libero quae veniam cum aperiam quis natus corrupti sunt.
				</p>
			</div>
		</React.Fragment>
	);
};

export default HomePage;
