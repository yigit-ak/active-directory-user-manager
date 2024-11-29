function CreateUser() {
  const handleSubmit = (event) => {
    event.preventDefault();
    // TODO: send form to the server
  };
  
  return (
    <div className="create-user-page">
      <form className="create-user-form"  onSubmit={handleSubmit}>
        <div className="form-title">Create New User</div>

        <div className="create-user-form-grid">
          <div className="create-user-form-partition">
            <label htmlFor="name">Name:</label>
            <input type="text" id="name" name="name" placeholder="Write here" required />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="surname">Surname:</label>
            <input type="text" id="surname" name="surname" placeholder="Write here" required />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="Write here" required />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="phone">Phone Number:</label>
            <input type="text" id="phone" name="phone" placeholder="Write here" required />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="vendor">Vendor*:</label>
            <select id="vendor" name="vendor" required>
              <option value="" >Select vendor</option>
              <option value="companyId1">Company 1</option> {/*TODO: delete this line*/}
              {/* TODO: fetch vendors from DB */}
            </select>
            <small>*If the vendor is not in the list, open a new request.</small>
          </div>
        </div>

        <button type="submit">Create</button>
      </form>
    </div>
  )
}
    
export default CreateUser
