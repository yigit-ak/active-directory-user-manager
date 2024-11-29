import { useState } from "react";

function UserLookup() {
  const [userId, setUserId] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    // TODO: send form to the server
  };

  const handleInputChange = (event) => {
    setUserId(event.target.value);
  };

  return (
    <div className="user-lookup-page">
      <form id="user-id-form" onSubmit={handleSubmit}>
        <label htmlFor="user-id">User Lookup</label>
        <input type="text" id="user-id" name="user-id" onChange={handleInputChange} placeholder="Enter user Id here" required />
        <button type="submit">Search</button>
      </form>
    </div>
  )
}
  
export default UserLookup
