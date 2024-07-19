import React, { useState } from "react";
import "../assets/styles/DropDown.css";
const Dropdown = ({
	items,
	getStatusColor,
	dropdownClassName,
	handleChoose,
}) => {
	return (
		<div className={`search-results-dropdown-${dropdownClassName}`}>
			{items.map((item, index) => (
				<div key={index} className="search-result-item">
					<div
						className="post-user-email"
						onClick={(e) => handleChoose(item.email)}
					>
						<div className="profile-header">
							<div className="profile-user-picture">
								<img src={item.profilePictureUrl} alt="Profile" />
							</div>

							<div className="profile-info">
								<div className="profile-name-status">
									<h3>
										{item.userName}
										<span
											className="status-circle"
											style={{
												backgroundColor: getStatusColor(item.onlineStatus),
											}}
										></span>
									</h3>
								</div>
								<p>{item.email}</p>
							</div>
						</div>
					</div>
				</div>
			))}
		</div>
	);
};

export default Dropdown;
