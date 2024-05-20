import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetTherapyDetailComponent } from 'app/entities/dataset-therapy/dataset-therapy-detail.component';
import { DatasetTherapy } from 'app/shared/model/dataset-therapy.model';

describe('Component Tests', () => {
  describe('DatasetTherapy Management Detail Component', () => {
    let comp: DatasetTherapyDetailComponent;
    let fixture: ComponentFixture<DatasetTherapyDetailComponent>;
    const route = { data: of({ datasetTherapy: new DatasetTherapy(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetTherapyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatasetTherapyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetTherapyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datasetTherapy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
