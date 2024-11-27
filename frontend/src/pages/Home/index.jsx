import OptionButton from "./OptionButton"

function Home() {
  return (
    <div className="home">
      <OptionButton link='/UserLookup' title={'User Lookup'} />
      <OptionButton link='/CreateUser' title={'Create New User for Vendor'} />
    </div>
  )
}

export default Home
