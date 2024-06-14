
import { HiEye, HiEyeOff } from 'react-icons/hi';
import '../assets/styles/Group.css';

const Group = ({ group }) => {

  return (
    <div className="group">
      <div className="group-header">
        <div className="group-picture"></div>
        <div className="group-creator-details">
          <a href={"/group/viewgroup/"+group.id}>{group.groupName} </a>
          {group.status==="public"?<HiEye/>:<HiEyeOff/>}
          <p>{group.dateCreated}</p>
        </div>
      </div>
      <div className="group-description">
        <p>{group.description}</p>
      </div>
    </div>
  );
};

export default Group;
