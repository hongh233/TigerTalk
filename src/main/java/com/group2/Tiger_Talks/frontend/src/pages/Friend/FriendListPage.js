import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import NavBar from "../../components/NavBar";
import Header from "../../components/Header";
import "../../assets/styles/FriendListPage.css"; // New CSS file
import UserComponent from "../../components/UserComponent";
import SearchBar from "../../components/SearchBar";
import {filterUsers} from "../../utils/filterUsers";
import {handleDelete, getAllFriendsByEmail} from "../../axios/FriendAxios";

const FriendListPage = () => {
    const user = useSelector((state) => state.user.user);
    const [friends, setFriends] = useState([]);
    const [allFriends, setAllFriends] = useState([]);
    const [searchFriendQuery, setSearchFriendQuery] = useState("");

    const [isNavVisible, setIsNavVisible] = useState(false);

    const handleDeleteFriend = (id) => {
        setFriends(friends.filter((friend) => friend.id !== id));
    };

    useEffect(() => {
        const fetchFriends = async () => {
            if (user && user.email) {
                try {
                    const responseData = await getAllFriendsByEmail(user.email);
                    if (responseData.length > 0) {
                        setAllFriends(responseData);
                        setFriends(responseData);
                    }
                } catch (error) {
                    console.error("Failed to fetch friends", error);
                }
            }
        };

        fetchFriends();
    }, [user]);

    useEffect(() => {
        if (searchFriendQuery) {
            const filteredFriends = filterUsers(allFriends, searchFriendQuery);
            setFriends(filteredFriends);
        } else {
            setFriends(allFriends);
        }
    }, [searchFriendQuery, allFriends]);

    return (
        <div className="friend-list-page">
            <Header/>
            <div className="menu-toggle" onClick={() => setIsNavVisible(!isNavVisible)}>
                <div></div>
                <div></div>
                <div></div>
            </div>


			<div className={`content ${isNavVisible ? "nav-visible" : ""}`}>
                <div className={`sidebar ${isNavVisible ? "visible" : ""}`}>
                    <button className="close-btn" onClick={() => setIsNavVisible(false)}>×</button>
                    <NavBar />
                </div>
                <div className="friend-list-content">
                    <h3>Friend list:</h3>
                    <div className="friend-list-search-bar">
                        <SearchBar
                            searchType="friend"
                            setSearchFriendQuery={setSearchFriendQuery}
                            dropdownClassName="friend"
                            searchBarClassName="friend"
                        />
                    </div>

                    {friends.length > 0 ? (
                        friends.map((friend) => (
                            <UserComponent
                                key={friend.email}
                                user={friend}
                                userEmail={user.email}
                                onDelete={handleDeleteFriend}
                                handleDeleteFn={() => handleDelete(user.email, friend.email)}
                            />
                        ))
                    ) : (
                        <div className="no-friends">
                            <p>You have no friends.</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default FriendListPage;
