import { useEffect, useState } from "react";
import { getAllVendors, createUser } from "@/api/userApi";
import GoBack from "@/components/GoBack";

function CreateUser() {
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    vendor: ""
  });

  const [vendors, setVendors] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  useEffect(() => {
    getAllVendors()
      .then(setVendors)
      .catch(err => {
        console.error(err);
        setMessage("Failed to load vendors.");
      });
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      await createUser(form);
      setMessage("User created successfully.");
      setForm({ firstName: "", lastName: "", email: "", phoneNumber: "", vendor: "" });
    } catch (error) {
      console.error(error);
      setMessage("Failed to create user.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="create-user-page">
      <form className="create-user-form" onSubmit={handleSubmit}>
        <GoBack link="/" />
        <div className="form-title">Create New User</div>

        <div className="create-user-form-grid">
          <div className="create-user-form-partition">
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              id="name"
              name="firstName"
              placeholder="Write here"
              required
              value={form.firstName}
              onChange={(e) => setForm({ ...form, firstName: e.target.value })}
            />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="surname">Surname:</label>
            <input
              type="text"
              id="surname"
              name="lastName"
              placeholder="Write here"
              required
              value={form.lastName}
              onChange={(e) => setForm({ ...form, lastName: e.target.value })}
            />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Write here"
              required
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
            />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="phone">Phone Number:</label>
            <input
              type="text"
              id="phone"
              name="phoneNumber"
              placeholder="Write here"
              required
              value={form.phoneNumber}
              onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })}
            />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="vendor">Vendor*:</label>
            <select
              id="vendor"
              name="vendor"
              required
              value={form.vendor}
              onChange={(e) => setForm({ ...form, vendor: e.target.value })}
            >
              <option value="">Select vendor</option>
              {vendors.map((v) => (
                <option key={v.name} value={v.name}>
                  {v.name}
                </option>
              ))}
            </select>
            <small>*If the vendor is not in the list, open a new request.</small>
          </div>
        </div>

        {message && <p className="form-message">{message}</p>}
        <button type="submit" disabled={loading}>
          {loading ? "Creating..." : "Create"}
        </button>
      </form>
    </div>
  );
}

export default CreateUser;
