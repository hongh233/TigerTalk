import {MdAccessTimeFilled, MdCheckCircle, MdRemoveCircle} from "react-icons/md";
import "../../assets/styles/Components/Main/StatusIcon.css";
import {IoMdCloseCircle} from "react-icons/io";
import React from "react";


/* How to use: set direct parent box to be:
    position: relative;
*/
const StatusIcon = ({ status }) => {

    switch (status) {
        case "available":
            return <MdCheckCircle
                style={{ color: '#4caf50' }}
                className="status-adjust-creation-small"
            />;
        case "busy":
            return <MdRemoveCircle
                style={{ color: '#f44336' }}
                className="status-adjust-creation-small"
            />;
        case "away":
            return <MdAccessTimeFilled
                style={{ color: '#ff9800' }}
                className="status-adjust-creation-small"
            />;
        case "offline":
            return <IoMdCloseCircle
                style={{ color: '#9e9e9e' }}
                className="status-adjust-creation-small"
            />;
    }
};

export default StatusIcon;