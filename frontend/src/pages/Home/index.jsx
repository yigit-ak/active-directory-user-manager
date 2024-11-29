import OptionButton from "./OptionButton"

function Home() {
  return (
    <div className="homepage">
      <OptionButton link='/user-lookup' title={'User Lookup'} />
      <OptionButton link='/create-user' title={'Create New User for Vendor'} />
    </div>
  )
}

export default Home
