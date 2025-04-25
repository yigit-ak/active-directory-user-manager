import { useEffect, useState } from "react";
import { getAllVendors, createUser } from "@/api.js";
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
  const [fieldErrors, setFieldErrors] = useState({});

  useEffect(() => {
    getAllVendors()
      .then(setVendors)
      .catch(error => {
        console.error(error);
        setMessage(error.message || "Failed to load vendors.");
      });
  }, []);

  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setFieldErrors({ ...fieldErrors, [e.target.name]: null }); // Clear field-specific error on change
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");
    setFieldErrors({});

    try {
      await createUser(form);
      setMessage("User created successfully.");
      setForm({
        firstName: "",
        lastName: "",
        email: "",
        phoneNumber: "",
        vendor: ""
      });
    } catch (error) {
      console.error(error);

      // Check if backend sent validation errors
      if (error.response?.status === 400 && typeof error.response.data === "object") {
        setFieldErrors(error.response.data); // Set per-field errors
        setMessage("Please fix the errors and try again.");
      } else {
        setMessage(error.message || "Failed to create user.");
      }
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
              onChange={handleInputChange}
            />
            {fieldErrors.firstName && <small className="error-text">{fieldErrors.firstName}</small>}
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
              onChange={handleInputChange}
            />
            {fieldErrors.lastName && <small className="error-text">{fieldErrors.lastName}</small>}
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
              onChange={handleInputChange}
            />
            {fieldErrors.email && <small className="error-text">{fieldErrors.email}</small>}
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
              onChange={handleInputChange}
            />
            {fieldErrors.phoneNumber && <small className="error-text">{fieldErrors.phoneNumber}</small>}
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="vendor">Vendor*:</label>
            <select
              id="vendor"
              name="vendor"
              required
              value={form.vendor}
              onChange={handleInputChange}
            >
              <option value="">Select vendor</option>
              {vendors.map((v) => (
                <option key={v.name} value={v.name}>
                  {v.name}
                </option>
              ))}
            </select>
            {fieldErrors.vendor && <small className="error-text">{fieldErrors.vendor}</small>}
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
