import React from "react";
import "../../assets/styles/Components/Admin/ToggleSwitch.css";


const ToggleSwitch = ({ checked, onChange, buttonType }) => {
    return (
        <label className={`admin-page-switch ${buttonType === "admin" ? "admin-switch-admin" : "admin-switch-activate"}`}>
            <input type="checkbox" checked={checked} onChange={onChange} />
            <span className="admin-toggle-switch-slider-round"></span>
        </label>
    );
};

export default ToggleSwitch;