import OptionButton from "./OptionButton"

function Home() {
  return (
    <>
      <OptionButton link='/UserLookup' title={'User Lookup'} />
      <OptionButton link='/CreateUser' title={'Create New User for Vendor'} />
    </>
  )
}

export default Home
