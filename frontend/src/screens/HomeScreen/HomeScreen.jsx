import Slider from "../../components/slider/Slider";
import useNewsApi from "../../hooks/UseNewsApi";
import './HomeScreen.css'

const HomeScreen = () => {
    const { news, loading, error } = useNewsApi();

    return <div className="home">
        <Slider loading={loading} news={news} />
    </div>
}


export default HomeScreen;
