import React, { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import Algorithm from "../data/algorithm";
import ReactMarkdown from "react-markdown";
import styled from "@emotion/styled";
import rehypeRaw from "rehype-raw";
import ReactDom from "react-dom";

const input = `## 컴퓨터의 구성

컴퓨터가 가지는 구성에 대해 알아보자

<br>

컴퓨터 시스템은 크게 하드웨어와 소프트웨어로 나누어진다.

**하드웨어** : 컴퓨터를 구성하는 기계적 장치

**소프트웨어** : 하드웨어의 동작을 지시하고 제어하는 명령어 집합

<br>

#### 하드웨어

---

- 중앙처리장치(CPU)
- 기억장치 : RAM, HDD
- 입출력 장치 : 마우스, 프린터

#### 소프트웨어

---

- 시스템 소프트웨어 : 운영체제, 컴파일러
- 응용 소프트웨어 : 워드프로세서, 스프레드시트

<br>

먼저 하드웨어부터 살펴보자

<img src="data:image/png;base64,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">



하드웨어는 중앙처리장치(CPU), 기억장치, 입출력장치로 구성되어 있다.

이들은 시스템 버스로 연결되어 있으며, 시스템 버스는 데이터와 명령 제어 신호를 각 장치로 실어나르는 역할을 한다.

<br>

##### 중앙처리장치(CPU)

인간으로 따지면 두뇌에 해당하는 부분

주기억장치에서 프로그램 명령어와 데이터를 읽어와 처리하고 명령어의 수행 순서를 제어함
중앙처리장치는 비교와 연산을 담당하는 <strong>산술논리연산장치(ALU)</strong>와 명령어의 해석과 실행을 담당하는 **제어장치**, 속도가 빠른 데이터 기억장소인 **레지스터**로 구성되어있음

개인용 컴퓨터와 같은 소형 컴퓨터에서는 CPU를 마이크로프로세서라고도 부름

<br>

##### 기억장치

프로그램, 데이터, 연산의 중간 결과를 저장하는 장치

주기억장치와 보조기억장치로 나누어지며, RAM과 ROM도 이곳에 해당함. 실행중인 프로그램과 같은 프로그램에 필요한 데이터를 일시적으로 저장한다.

보조기억장치는 하드디스크 등을 말하며, 주기억장치에 비해 속도는 느리지만 많은 자료를 영구적으로 보관할 수 있는 장점이 있다.

<br>

##### 입출력장치

입력과 출력 장치로 나누어짐. 

입력 장치는 컴퓨터 내부로 자료를 입력하는 장치 (키보드, 마우스 등)

출력 장치는 컴퓨터에서 외부로 표현하는 장치 (프린터, 모니터, 스피커 등)

<br>

<br>

#### 시스템 버스

> 하드웨어 구성 요소를 물리적으로 연결하는 선

각 구성요소가 다른 구성요소로 데이터를 보낼 수 있도록 통로가 되어줌

용도에 따라 데이터 버스, 주소 버스, 제어 버스로 나누어짐

<br>

##### 데이터 버스

중앙처리장치와 기타 장치 사이에서 데이터를 전달하는 통로

기억장치와 입출력장치의 명령어와 데이터를 중앙처리장치로 보내거나, 중앙처리장치의 연산 결과를 기억장치와 입출력장치로 보내는 '양방향' 버스임

##### 주소 버스

데이터를 정확히 실어나르기 위해서는 기억장치 '주소'를 정해주어야 함.

주소버스는 중앙처리장치가 주기억장치나 입출력장치로 기억장치 주소를 전달하는 통로이기 때문에 '단방향' 버스임

##### 제어 버스

주소 버스와 데이터 버스는 모든 장치에 공유되기 때문에 이를 제어할 수단이 필요함

제어 버스는 중앙처리장치가 기억장치나 입출력장치에 제어 신호를 전달하는 통로임

제어 신호 종류 : 기억장치 읽기 및 쓰기, 버스 요청 및 승인, 인터럽트 요청 및 승인, 클락, 리셋 등

제어 버스는 읽기 동작과 쓰기 동작을 모두 수행하기 때문에 '양방향' 버스임

<br>

컴퓨터는 기본적으로 **읽고 처리한 뒤 저장**하는 과정으로 이루어짐

(READ → PROCESS → WRITE)

이 과정을 진행하면서 끊임없이 주기억장치(RAM)과 소통한다. 이때 운영체제가 64bit라면, CPU는 RAM으로부터 데이터를 한번에 64비트씩 읽어온다.

<br>`;

const MarkDownStyle = styled.div`
  font-size: 1rem;
  line-height: 2.5rem;
`;

const SubjectDetail = ({}) => {
  const navigate = useNavigate();
  const location = useLocation();
  // console.log(location.state);
  // console.log(location.state.csType);
  // console.log(location.state.studyData);
  // console.log(location.state.studyData[csType]);
  // console.log(location.state.studyData[csName]);
  /* 
  버튼 클릭했을때 선택된 과목 = location.state.csType
  전체 공부 데이터 = location.state.studyData
  */
  const studyData = location.state.studyData;
  // const [studyIndex, setStudyIndex] = useState([]);
  // const [selected, setSelected] = useState("");

  useEffect(() => {
    //
  }, []);

  // 선택한 과목과 데이터상의 과목이름이 같을때
  const mapStudyData = studyData.map((a, index) => {
    if (location.state.csType === a.csType) {
      console.log(a.csName);
      return (
        <div
          key={index}
          onClick={() => {
            navigate(`/cs-study/subject-detail/${a.csType}/${a.csCode}`, {
              state: {
                /*
                csType: a.csType, : 선택했던 과목
                csCode: a.csCode, : 선택한 목록의 csCode
                studyData: studyData, : 전체 데이터
                selected: a.csName, : 선택한 과목에서 클릭한 목록
                 */
                csType: a.csType,
                csCode: a.csCode,
                studyData: studyData,
                selected: a.csName,
              },
            });
            // alert("클릭적용");
          }}
        >
          {a.csName}
        </div>
      );
      // setStudyIndex([...studyData, a.csName]);
      // console.log(studyIndex);
    }
  });

  return (
    <>
      <h1>선택과목 : {location.state.csType}</h1>
      <div>{mapStudyData}</div>
      <ReactMarkdown rehypePlugins={[rehypeRaw]} children={input} />,
    </>
  );
};

export default SubjectDetail;
