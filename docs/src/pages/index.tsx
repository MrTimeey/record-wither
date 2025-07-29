import React from 'react';
import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';
import styles from './index.module.css';


export default function Home() {
    const {siteConfig} = useDocusaurusContext();
    return (
        <Layout
            title={`Record Wither`}
            description="Convenient withers for Java records">

            {/* Hero Section */}
            <header className={clsx('hero hero--primary', styles.heroBanner)}>
                <div className="container">
                    <h1 className="hero__title">{siteConfig.title}</h1>
                    <p className="hero__subtitle">{siteConfig.tagline}</p>
                    <div className={styles.buttons}>
                        <Link
                            className="button button--secondary button--lg"
                            to="/docs/getting-started">
                            Getting Started 🚀
                        </Link>
                    </div>
                    <div style={{marginTop: '1rem'}}>
                        {/* Badges */}
                        <img src="https://img.shields.io/maven-central/v/io.github.mrtimeey/record-wither" alt="Maven Central" style={{marginRight: '8px'}} />
                        <img src="https://javadoc.io/badge2/io.github.mrtimeey/record-wither/javadoc.svg" alt="Javadoc" style={{marginRight: '8px'}} />
                        <a href="https://github.com/mrtimeey/record-wither">
                            <img src="https://img.shields.io/github/stars/mrtimeey/record-wither?style=social" alt="GitHub Stars" />
                        </a>
                    </div>
                </div>
            </header>

            <main>

                {/* Code Example */}
                <section className={styles.codeExample}>
                    <div className="container">
                        <h2 style={{textAlign: 'center', marginTop:'1.5rem'}}>Quick Example</h2>
                        <pre>
      <code className="language-java">
{`record Person(String name, int age) implements Withable<Person> {}

// Wither in action:
Person jonas = new Person("Jonas", 30);
Person older = jonas.with(Person::age, 42);`}
      </code>
    </pre>
                    </div>
                </section>

                {/* Why Section */}
                <section className={styles.whySection}>
                    <div className={styles.container}>
                        <h2 style={{textAlign: 'center'}}>Why Record Wither?</h2>
                        <ul>
                            <li>Immutable by Design – generate <code>with</code>-methods without breaking immutability</li>
                            <li>Lightweight Utility – just a small library, no heavy dependencies</li>
                            <li>Integration Friendly – works seamlessly with Maven, Gradle, etc.</li>
                            <li>No more boilerplate wither methods</li>
                            <li>Works out-of-the-box with Java Records</li>
                        </ul>
                    </div>
                </section>


                {/* Final Call to Action */}
                <section style={{padding: '3rem 0', textAlign: 'center'}}>
                    <Link className="button button--primary button--lg" to="/docs/getting-started">
                        Get Started with Record Wither 🚀
                    </Link>
                </section>
            </main>
        </Layout>
    );
}
