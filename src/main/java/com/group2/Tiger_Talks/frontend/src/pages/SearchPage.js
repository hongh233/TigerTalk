import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import Header from "../components/Header";
import NavBar from "../components/NavBar";
import Dropdown from "../components/DropDown";
import Group from "./../components/Group";
import "./../assets/styles/SearchPage.css";
const SearchPage = () => {
	const { globalUsers } = useSelector((state) => state.globalUsers);
	const { globalGroups } = useSelector((state) => state.globalGroups);
	const navigate = useNavigate();
	const handleChoose = (email) => {
		navigate(`/profile/${email}`);
	};
	return (
		<div className="search-page-container">
			<div className="search-content-header">
				<Header />
			</div>
			<div className="search-page">
				<div className="search-content-nav">
					<NavBar />
				</div>

				<div className="search-content-container">
					<h2>Users matched with keyword:</h2>
					{globalUsers && globalUsers.length > 0 ? (
						<Dropdown
							items={globalUsers}
							dropdownClassName="global"
							handleChoose={handleChoose}
						/>
					) : (
						<p>No users found with this keyword</p>
					)}

					<h2>Groups matched with keyword:</h2>

					{globalGroups && globalGroups.length > 0 ? (
						globalGroups.map((group) => (
							<div className="global-search-group-list">
								<Group key={group.groupId} group={group} />
							</div>
						))
					) : (
						<p>No groups found with this keyword</p>
					)}
				</div>
			</div>
		</div>
	);
};

export default SearchPage;
