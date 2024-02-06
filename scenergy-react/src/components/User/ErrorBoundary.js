// ErrorBoundary.js
import React, { Component } from 'react';

class ErrorBoundary extends Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    static getDerivedStateFromError(error) {
        return { hasError: true };
    }

    componentDidCatch(error, errorInfo) {
        console.error('에러 발생:', error, errorInfo);
    }

    render() {
        if (this.state.hasError) {
            return (
                <div>
                    <h1>Unexpected Application Error!</h1>
                    {/* 추가적인 에러 메시지 또는 도움말을 여기에 추가할 수 있습니다. */}
                </div>
            );
        }

        return this.props.children;
    }
}

export default ErrorBoundary;
