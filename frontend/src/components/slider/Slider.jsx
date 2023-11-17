import React, { useState, useEffect } from 'react';
import './Slider.css';
import SliderDots from "../silderDots/SliderDots";

const ImageSlider = ({ news }) => {
    const [currentIndex, setCurrentIndex] = useState(0);

    const goToSlide = (index) => {
        setCurrentIndex(index);
    };

    const goToNextSlide = () => {
        const newIndex = (currentIndex + 1) % news.length;
        setCurrentIndex(newIndex);
    };

    const goToPrevSlide = () => {
        const newIndex = (currentIndex - 1 + news.length) % news.length;
        setCurrentIndex(newIndex);
    };

    useEffect(() => {
        const intervalId = setInterval(goToNextSlide, 5000);

        return () => {
            clearInterval(intervalId);
        };
    }, [currentIndex]);

    return (
        <div className="slider-container">
            <div className="slider-area">
                <button className="arrow prev" onClick={goToPrevSlide}>
                    &#60;
                </button>
                <div className="slider">
                    {news.map((news_, index) => (
                        <div key={index} className={`slide ${index === currentIndex ? 'active' : 'non-active'}`} >
                            <p className="slider-text-area">{news_.title}</p>
                            <img src={news_.imageUrl} alt={`slide-${index}`} />
                        </div>
                    ))}
                </div>
                <button className="arrow next" onClick={goToNextSlide}>
                    &#62;
                </button>
            </div>
            <SliderDots slides={news} goToSlide={goToSlide} currentIndex={currentIndex}/>
        </div>
    );
};

export default ImageSlider;
