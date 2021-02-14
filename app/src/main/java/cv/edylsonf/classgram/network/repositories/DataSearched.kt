package cv.edylsonf.classgram.network.repositories

// Call Back
interface DataSearched {

    fun onDataSearchedSuccess(search: List<Search>)

    fun onDataSearchedFailed()
}