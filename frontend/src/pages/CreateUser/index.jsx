import { useEffect, useState } from "react";
import GoBack from "@/components/GoBack";
import { fetchAllVendors, createUser } from "@/api";

function CreateUser() {
  const [vendors, setVendors] = useState([]);
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    vendor: "",
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Fetch vendors on component mount
  useEffect(() => {
    const loadVendors = async () => {
      try {
        const vendorList = await fetchAllVendors();
        setVendors(vendorList);
      } catch (err) {
        setError("Failed to load vendors.");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    loadVendors();
  }, []);

  // Handle form changes
  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Handle form submission
  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");

    try {
      await createUser({
        vendor: formData.vendor,
        commonName: `${formData.firstName} ${formData.lastName}`,
        samAccountName: formData.email.split("@")[0], // Assuming samAccountName is the email prefix
        displayName: `${formData.firstName} ${formData.lastName}`,
        firstName: formData.firstName,
        lastName: formData.lastName,
        email: formData.email,
        phoneNumber: formData.phoneNumber,
        isEnabled: true, // Assuming new users are enabled by default
      });
      alert("User created successfully!");
    } catch (err) {
      setError("Failed to create user.");
      console.error(err);
    }
  };

  return (
    <div className="create-user-page">
      <form className="create-user-form"  onSubmit={handleSubmit}>
      
      <GoBack link='/'/>
      
        <div className="form-title">Create New User</div>

        <div className="create-user-form-grid">
          <div className="create-user-form-partition">
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              placeholder="Write here"
              required
            />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="lastName">Surname:</label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              placeholder="Write here"
              required
            />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Write here"
              required
            />         
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="phoneNumber">Phone Number:</label>
            <input
              type="text"
              id="phoneNumber"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleChange}
              placeholder="Write here"
              required
            />
          </div>

          <div className="create-user-form-partition">
            <label htmlFor="vendor">Vendor*:</label>
            <select
              id="vendor"
              name="vendor"
              value={formData.vendor}
              onChange={handleChange}
              required
            >
              <option value="">Select vendor</option>
              {loading ? (
                <option disabled>Loading vendors...</option>
              ) : (
                vendors.map((vendor) => (
                  <option key={vendor.name} value={vendor.name}>
                    {vendor.name}
                  </option>
                ))
              )}
            </select>
            <small>*If the vendor is not in the list, open a new request.</small>
          </div>
        </div>

        {error && <p className="error">{error}</p>}

        <button type="submit">Create</button>
      </form>
    </div>
  )
}
    
export default CreateUser
